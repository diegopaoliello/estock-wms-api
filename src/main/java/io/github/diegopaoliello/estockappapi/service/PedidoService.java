package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.PedidoStatusAtualException;
import io.github.diegopaoliello.estockappapi.exception.PedidoStatusException;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoRepository;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoStatusRepository;

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

	@Autowired
	PedidoStatusRepository pedidoStatusRepository;

	private Boolean isConcluindoPedido = false;

	public PedidoService() {
		super("Pedido de Compras");
	}

	@Override
	public Pedido salvar(Pedido pedido) {
		try {
			PedidoStatus status = pedidoStatusRepository.findByCodigo("ABERTO");

			pedido.setStatus(status);

			pedido = super.salvar(pedido);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return pedido;
	}

	@Override
	@Transactional
	public void atualizar(Integer id, Pedido pedidoAtualizado) {
		PedidoStatus statusAtualizado = pedidoAtualizado.getStatus();

		repository.findById(id).map(pedido -> {

			permiteAlterarStatus(pedido.getStatus(), statusAtualizado);
			isConcluindoPedido = isConcluindoPedido(pedido.getStatus(), statusAtualizado);

			pedido = pedidoAtualizado;
			pedido.setId(id);
			pedido.setUsuario(super.usuarioService.acharUsuarioAutenticado());

			pedido = repository.save(pedido);

			if (isConcluindoPedido) {
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
	public void atualizarStatus(Integer id, String status) {
		try {
			Pedido pedido = acharPorId(id);
			PedidoStatus statusAtualizado = pedidoStatusRepository.findByCodigo(status);

			permiteAlterarStatus(pedido.getStatus(), statusAtualizado);
			isConcluindoPedido = isConcluindoPedido(pedido.getStatus(), statusAtualizado);

			super.repository.updateStatus(id, statusAtualizado);

			if (isConcluindoPedido) {
				List<PedidoItem> itensPedido = itemPedidoService.acharPorPedido(pedido);

				for (PedidoItem itemPedido : itensPedido) {
					entradaEstoqueService.gerar(itemPedido);
				}
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	private boolean isConcluindoPedido(PedidoStatus statusAnterior, PedidoStatus statusAtualizado) {
		return (statusAnterior.getCodigo().equals("APROVADO")  && statusAtualizado.getCodigo().equals("CONCLUIDO"));
	}

	private void permiteAlterarStatus(PedidoStatus statusAnterior, PedidoStatus statusAtualizado) {
		if (!statusAtualizado.getCodigo().equals("ABERTO")
				&& statusAtualizado.getCodigo().equals(statusAnterior.getCodigo())) {
			throw new PedidoStatusAtualException(statusAtualizado.getDescricao());
		}

		if (statusAtualizado.getCodigo().equals("APROVADO") && !statusAnterior.getCodigo().equals("ABERTO")) {
			throw new PedidoStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.getCodigo().equals("REPROVADO") && !statusAnterior.getCodigo().equals("ABERTO")) {
			throw new PedidoStatusException(statusAtualizado.getAcao());
		}

		if (statusAtualizado.getCodigo().equals("CONCLUIDO") && !statusAnterior.getCodigo().equals("APROVADO")) {
			throw new PedidoStatusException(statusAtualizado.getAcao());
		}
	}
}