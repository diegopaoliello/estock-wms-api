package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Cliente;
import io.github.diegopaoliello.estockappapi.model.entity.ItemPedido;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.entity.ServicoPrestado;
import io.github.diegopaoliello.estockappapi.model.repository.ClienteRepository;
import io.github.diegopaoliello.estockappapi.model.repository.ItemPedidoRepository;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoRepository;
import io.github.diegopaoliello.estockappapi.model.repository.ProdutoRepository;
import io.github.diegopaoliello.estockappapi.rest.dto.PedidoDTO;
import io.github.diegopaoliello.estockappapi.rest.dto.ServicoPrestadoDTO;
import io.github.diegopaoliello.estockappapi.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	private final PedidoRepository repository;
	private final PedidoService service;
	private final ClienteRepository clienteRepository;
	private ItemPedidoRepository itemPedidoRepository;
	private ProdutoRepository produtoRepository;

	@Autowired
	public PedidoController(PedidoRepository repository, PedidoService service, ClienteRepository clienteRepository,
			ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository) {
		this.repository = repository;
		this.service = service;
		this.clienteRepository = clienteRepository;
		this.itemPedidoRepository = itemPedidoRepository;
		this.produtoRepository = produtoRepository;
	}

	@GetMapping
	public List<Pedido> obterTodos() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido salvar(@RequestBody @Valid PedidoDTO dto) {
		Integer idCliente = dto.getIdCliente();

		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente."));

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);

		pedido = repository.save(pedido);

		for (ItemPedido ip : dto.getItens()) {
			Produto produto = produtoRepository.findById(ip.getProduto().getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
			ip.setPedido(pedido);
			ip.setProduto(produto);
		}

		itemPedidoRepository.saveAll(dto.getItens());
		pedido.setItens(dto.getItens());

		return pedido;
	}

	@GetMapping("{id}")
	public Pedido acharPorId(@PathVariable Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		repository.findById(id).map(pedido -> {
			repository.delete(pedido);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}

//	@PutMapping("{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Pedido produtoNew) {
//		repository.findById(id).map(produtoOld -> {
//			produtoOld.setCategoria(produtoNew.getCategoria());
//			produtoOld.setCodigo(produtoNew.getCodigo());
//			produtoOld.setDescricao(produtoNew.getDescricao());
//			produtoOld.setUnidadeMedida(produtoNew.getUnidadeMedida());
//			return repository.save(produtoOld);
//		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
//	}
}
