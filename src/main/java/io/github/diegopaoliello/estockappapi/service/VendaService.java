package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.VendaStatusAtualException;
import io.github.diegopaoliello.estockappapi.exception.VendaStatusException;
import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;
import io.github.diegopaoliello.estockappapi.model.entity.VendaStatus;
import io.github.diegopaoliello.estockappapi.model.repository.VendaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VendaService extends AbstractService<Venda, VendaRepository> {
	@Autowired
	private VendaItemService itemVendaService;

	@Autowired
	private EstoqueSaidaService saidaEstoqueService;

	private Boolean isConcluindoVenda = false;

	public VendaService() {
		super(Venda.class);
	}

	@Override
	public Venda salvar(Venda venda) {
		try {
			venda.setStatus(VendaStatus.ABERTO);

			venda = super.salvar(venda);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return venda;
	}

	@Override
	public void atualizar(Integer id, Venda vendaAtualizado) {
		VendaStatus statusAtualizado = vendaAtualizado.getStatus();

		repository.findById(id).map(venda -> {

			permiteAlterarStatus(venda.getStatus(), statusAtualizado);
			isConcluindoVenda = isConcluindoVenda(venda.getStatus(), statusAtualizado);

			venda = vendaAtualizado;
			venda.setId(id);
			venda.setUsuario(super.usuarioService.findAuthenticatedUser());

			venda = repository.save(venda);

			if (isConcluindoVenda) {
				List<VendaItem> itensVenda = itemVendaService.acharPorVenda(venda);

				for (VendaItem itemVenda : itensVenda) {
					saidaEstoqueService.gerar(itemVenda);
				}
			}

			return venda;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				this.entityClass.getSimpleName() + " não encontrado"));
	}

	@Transactional
	public void atualizarStatus(Integer id, VendaStatus statusAtualizado) {
		try {
			Venda venda = acharPorId(id);

			permiteAlterarStatus(venda.getStatus(), statusAtualizado);
			isConcluindoVenda = isConcluindoVenda(venda.getStatus(), statusAtualizado);

			super.repository.updateStatus(id, statusAtualizado);

			if (isConcluindoVenda) {
				List<VendaItem> itensVenda = itemVendaService.acharPorVenda(venda);

				for (VendaItem itemVenda : itensVenda) {
					saidaEstoqueService.gerar(itemVenda);
				}
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	private boolean isConcluindoVenda(VendaStatus statusAnterior, VendaStatus statusAtualizado) {
		return (statusAnterior == VendaStatus.APROVADO && statusAtualizado == VendaStatus.CONCLUIDO);
	}

	private void permiteAlterarStatus(VendaStatus statusAnterior, VendaStatus statusAtualizado) {
		if (!statusAtualizado.equals(VendaStatus.ABERTO) && statusAtualizado.equals(statusAnterior)) {
			throw new VendaStatusAtualException(statusAtualizado.getDescricao());
		}

		if (statusAtualizado.equals(VendaStatus.APROVADO) && !statusAnterior.equals(VendaStatus.ABERTO)) {
			throw new VendaStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.equals(VendaStatus.REPROVADO) && !statusAnterior.equals(VendaStatus.ABERTO)) {
			throw new VendaStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.equals(VendaStatus.CONCLUIDO) && !statusAnterior.equals(VendaStatus.APROVADO)) {
			throw new VendaStatusException(statusAtualizado.getAcao());
		}
	}
}