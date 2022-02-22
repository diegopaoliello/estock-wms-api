package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService extends AbstractService<Pedido, PedidoRepository> {
	@Autowired
	private PedidoItemService itemPedidoService;

	@Autowired
	private EstoqueEntradaService entradaEstoqueService;

	@Autowired
	private UsuarioService usuarioService;

	public PedidoService() {
		super(Pedido.class);
	}

	@Override
	public Pedido salvar(Pedido pedido) {
		pedido.setStatus(PedidoStatus.ABERTO);

		pedido = super.salvar(pedido);

		return pedido;
	}

	@Transactional
	public void atualizarStatus(Integer id, PedidoStatus statusAtualizado) {
		Pedido pedido = acharPorId(id);

		super.repository.updateStatus(id, statusAtualizado);

		if (isAprovandoPedido(pedido.getStatus(), statusAtualizado)) {
			EstoqueEntrada entradaEstoque = new EstoqueEntrada();
			List<PedidoItem> itensPedido = itemPedidoService.listar();

			for (PedidoItem item : itensPedido) {
				entradaEstoque.setItemPedido(item);
				entradaEstoque.setUsuario(usuarioService.findAuthenticatedUser());

				entradaEstoqueService.salvar(entradaEstoque);
			}
		}
	}

	private boolean isAprovandoPedido(PedidoStatus statusAnterior, PedidoStatus statusAtualizado) {
		return (statusAnterior == PedidoStatus.ABERTO && statusAtualizado == PedidoStatus.CONCLUIDO);
	}
}