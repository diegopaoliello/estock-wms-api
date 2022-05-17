package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.VendaStatusAtualException;
import io.github.diegopaoliello.estockappapi.exception.VendaStatusException;
import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;
import io.github.diegopaoliello.estockappapi.model.entity.VendaStatus;
import io.github.diegopaoliello.estockappapi.model.repository.VendaRepository;
import io.github.diegopaoliello.estockappapi.model.repository.VendaStatusRepository;

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

	@Autowired
	VendaStatusRepository vendaStatusRepository;

	private Boolean isAprovandoVenda = false;

	public VendaService() {
		super("Venda de Compras");
	}

	@Override
	public Venda salvar(Venda venda) {
		try {
			VendaStatus status = vendaStatusRepository.findByCodigo("ABERTO");

			venda.setStatus(status);

			venda = super.salvar(venda);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return venda;
	}

	@Override
	@Transactional
	public void atualizar(Integer id, Venda vendaAtualizado) {
		VendaStatus statusAtualizado = vendaAtualizado.getStatus();

		repository.findById(id).map(venda -> {

			permiteAlterarStatus(venda.getStatus(), statusAtualizado);
			isAprovandoVenda = isAprovandoVenda(venda.getStatus(), statusAtualizado);

			venda = vendaAtualizado;
			venda.setId(id);
			venda.setUsuario(super.usuarioService.acharUsuarioAutenticado());

			venda = repository.save(venda);

			if (isAprovandoVenda) {
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
	public void atualizarStatus(Integer id, String status) {
		try {
			Venda venda = acharPorId(id);
			VendaStatus statusAtualizado = vendaStatusRepository.findByCodigo(status);

			permiteAlterarStatus(venda.getStatus(), statusAtualizado);
			isAprovandoVenda = isAprovandoVenda(venda.getStatus(), statusAtualizado);

			super.repository.updateStatus(id, statusAtualizado);

			if (isAprovandoVenda) {
				List<VendaItem> itensVenda = itemVendaService.acharPorVenda(venda);

				for (VendaItem itemVenda : itensVenda) {
					saidaEstoqueService.gerar(itemVenda);
				}
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	private boolean isAprovandoVenda(VendaStatus statusAnterior, VendaStatus statusAtualizado) {
		return (statusAnterior.getCodigo().equals("APROVADO") && statusAtualizado.getCodigo().equals("CONCLUIDO"));
	}

	private void permiteAlterarStatus(VendaStatus statusAnterior, VendaStatus statusAtualizado) {
		if (!statusAtualizado.getCodigo().equals("ABERTO")
				&& statusAtualizado.getCodigo().equals(statusAnterior.getCodigo())) {
			throw new VendaStatusAtualException(statusAtualizado.getDescricao());
		}

		if (statusAtualizado.getCodigo().equals("APROVADO") && !statusAnterior.getCodigo().equals("ABERTO")) {
			throw new VendaStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.getCodigo().equals("REPROVADO") && !statusAnterior.getCodigo().equals("ABERTO")) {
			throw new VendaStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.getCodigo().equals("CONCLUIDO") && !statusAnterior.getCodigo().equals("APROVADO")) {
			throw new VendaStatusException(statusAtualizado.getAcao());
		}
	}
}