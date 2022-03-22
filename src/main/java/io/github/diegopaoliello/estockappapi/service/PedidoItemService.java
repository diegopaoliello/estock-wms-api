package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoItemRepository;

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
}