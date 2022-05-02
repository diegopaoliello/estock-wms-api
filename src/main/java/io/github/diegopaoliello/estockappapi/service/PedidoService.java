package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.PedidoStatusAtualException;
import io.github.diegopaoliello.estockappapi.exception.PedidoStatusException;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoService extends AbstractService<Pedido, PedidoRepository> {
	@Autowired
	private PedidoItemService itemPedidoService;

	@Autowired
	private EstoqueEntradaService entradaEstoqueService;

	private Boolean isAprovandoPedido = false;

	public PedidoService() {
		super("Pedido de Compras");
	}

	@Override
	public Pedido salvar(Pedido pedido) {
		try {
			pedido.setStatus(PedidoStatus.ABERTO);

			pedido = super.salvar(pedido);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return pedido;
	}

	@Override
	public void atualizar(Integer id, Pedido pedidoAtualizado) {
		PedidoStatus statusAtualizado = pedidoAtualizado.getStatus();

		repository.findById(id).map(pedido -> {

			permiteAlterarStatus(pedido.getStatus(), statusAtualizado);
			isAprovandoPedido = isAprovandoPedido(pedido.getStatus(), statusAtualizado);

			pedido = pedidoAtualizado;
			pedido.setId(id);
			pedido.setUsuario(super.usuarioService.findAuthenticatedUser());

			pedido = repository.save(pedido);

			if (isAprovandoPedido) {
				List<PedidoItem> itensPedido = itemPedidoService.acharPorPedido(pedido);

				for (PedidoItem itemPedido : itensPedido) {
					entradaEstoqueService.gerar(itemPedido);
				}
			}

			return pedido;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				this.entityClass.getSimpleName() + " não encontrado"));
	}

	@Transactional
	public void atualizarStatus(Integer id, PedidoStatus statusAtualizado) {
		try {
			Pedido pedido = acharPorId(id);

			permiteAlterarStatus(pedido.getStatus(), statusAtualizado);
			isAprovandoPedido = isAprovandoPedido(pedido.getStatus(), statusAtualizado);

			super.repository.updateStatus(id, statusAtualizado);

			if (isAprovandoPedido) {
				List<PedidoItem> itensPedido = itemPedidoService.acharPorPedido(pedido);

				for (PedidoItem itemPedido : itensPedido) {
					entradaEstoqueService.gerar(itemPedido);
				}
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	private boolean isAprovandoPedido(PedidoStatus statusAnterior, PedidoStatus statusAtualizado) {
		return (statusAnterior == PedidoStatus.APROVADO && statusAtualizado == PedidoStatus.CONCLUIDO);
	}

	private void permiteAlterarStatus(PedidoStatus statusAnterior, PedidoStatus statusAtualizado) {
		if (!statusAtualizado.equals(PedidoStatus.ABERTO) && statusAtualizado.equals(statusAnterior)) {
			throw new PedidoStatusAtualException(statusAtualizado.getDescricao());
		}

		if (statusAtualizado.equals(PedidoStatus.APROVADO) && !statusAnterior.equals(PedidoStatus.ABERTO)) {
			throw new PedidoStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.equals(PedidoStatus.REPROVADO) && !statusAnterior.equals(PedidoStatus.ABERTO)) {
			throw new PedidoStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.equals(PedidoStatus.CONCLUIDO) && !statusAnterior.equals(PedidoStatus.APROVADO)) {
			throw new PedidoStatusException(statusAtualizado.getAcao());
		}
	}
}