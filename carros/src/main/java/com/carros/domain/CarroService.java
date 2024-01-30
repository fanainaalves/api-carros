package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {
    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros(){
        //Opção 1:
        List<CarroDTO> list = rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

        // Opção 2: com expressões Lambda
        //List<CarroDTO> list = carros.stream().map(c -> new CarroDTO(c)).collect(Collectors.toList());
        //ou
        //List<CarroDTO> list = carros.stream().map(CarroDTO:: new).collect(Collectors.toList());
        //ou
        //return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

        //opção 3
//        List<CarroDTO> list = new ArrayList<>();
//        for (Carro c : carros){
//            list.add(new carroDTO(c));
//        }
        return list;
    }

    public Optional<CarroDTO> getCarroById(Long id){
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::create);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo){
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }


    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(),"Não foi possivel inserir o registro");
        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.isNull(carro.getId(), "Não foi possivel atualizar o registro");
        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()){
            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            rep.save(db);
            return CarroDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possivel atualizar o registro");
        }
    }

    public boolean delete(Long id) {
        //Optional<CarroDTO> carro = getCarroById(id);
        if(getCarroById(id).isPresent()){
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
