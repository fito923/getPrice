package com.java.price.checking.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.price.checking.config.Constants;
import com.java.price.checking.entities.PricesEntity;




public class PricesRepositoryImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	Constants constantes;
	
	public List<PricesEntity> getData(HashMap<String, Object> conditions)
	{
		List<PricesEntity> retorno ;
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PricesEntity> query= cb.createQuery(PricesEntity.class);
		final Root<PricesEntity> root = query.from(PricesEntity.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field,value) ->
		{
			switch (field)
			{
				case "marca":
					predicates.add(cb.equal(root.get(field), (Integer)value));
					break;
				case "id":
					predicates.add(cb.equal(root.get(constantes.getIdProducto()),(String)value));
					break;				
				case "fecha":
					predicates.add(cb.lessThan(root.<Date>get(constantes.getFechaInicio()), (Date)value));
					predicates.add(cb.greaterThan(root.<Date>get(constantes.getFechaFin()), (Date)value));					
					break;
			}
			
		});
		
		if (predicates.size()==4) // si me han fijado todos dos criterios de búsquda , uso la prioridad para encontrar la tarifa que aplica
		{
			query.select(root).where(predicates.toArray(new Predicate[predicates.size()])).orderBy(cb.desc(root.get("prioridad")));
			retorno=entityManager.createQuery(query).getResultList().subList(0, 1);
		}
		else { 			
			query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));			
			retorno=entityManager.createQuery(query).getResultList();
		}
		
		return  	retorno;	
	}

}
