package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.ItemPedido;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.repository.ItemPedidoRepository;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoRepository;
import io.github.diegopaoliello.estockappapi.service.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	private ItemPedidoRepository itemPedidoRepository;

	public List<Pedido> findAll() {
		return repository.findAll();
	}

	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> retorno = repository.findById(id);
		return retorno.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id:" + id + " Tipo:" + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
	//	obj.setId(null);
	//	obj = repository.save(obj);
		obj = repository.saveAndFlush(obj);

//		for (ItemPedido ip : obj.getItens()) {
//			ip.setDesconto(0.0);
//			ip.setPreco(10.10);
//			ip.setPedido(obj);
//		}
//		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}

}