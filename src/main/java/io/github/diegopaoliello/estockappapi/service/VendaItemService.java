package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;
import io.github.diegopaoliello.estockappapi.model.repository.VendaItemRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class VendaItemService extends AbstractService<VendaItem, VendaItemRepository> {
	public VendaItemService() {
		super(VendaItem.class);
		/**/
	}

	public List<VendaItem> listar(Integer idVenda) {
		return repository.listar(idVenda);
	}

	public List<VendaItem> acharPorVenda(Venda venda) {
		return super.repository.findByVenda(venda).orElse(new ArrayList<VendaItem>());
	}
}