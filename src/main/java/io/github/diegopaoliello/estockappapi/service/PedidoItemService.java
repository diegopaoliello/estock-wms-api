package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoItemRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PedidoItemService extends AbstractService<PedidoItem, PedidoItemRepository> {
	public PedidoItemService() {
		super(PedidoItem.class);
		/**/
	}

	public List<PedidoItem> listar(Integer idPedido) {
		return repository.listar(idPedido);
	}

	public List<PedidoItem> acharPorPedido(Pedido pedido) {
		return super.repository.findByPedido(pedido).orElse(new ArrayList<PedidoItem>());
	}
}