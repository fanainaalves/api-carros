package com.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarroServiceTest {
	@Autowired
	private CarroService service;
	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");

		CarroDTO c = service.insert(carro);
		assertNotNull(c);
		Long id = c.getId();
		assertNotNull(id);

		//Buscar o Objeto
		Optional<CarroDTO> op = service.getCarroById(id);
		assertTrue(op.isPresent());

		c = op.get();
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());

		//Deletar o objeto criado por id
		service.delete(id);

		//Verificar se deletou o objeto
		assertFalse(service.getCarroById(id).isPresent());
	}

	@Test
	public void testLista(){
		List<CarroDTO> carros =  service.getCarros();
		assertEquals(30, carros.size());
	}
	@Test
	public void testListaPorTipo(){
		assertEquals(10, service.getCarrosByTipo("classicos").size());
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());
		assertEquals(0, service.getCarrosByTipo("x").size());
	}

	@Test
	public void testGet(){
		Optional<CarroDTO> op =  service.getCarroById(11L);
		assertTrue(op.isPresent());
		CarroDTO c= op.get();
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());
	}
}
