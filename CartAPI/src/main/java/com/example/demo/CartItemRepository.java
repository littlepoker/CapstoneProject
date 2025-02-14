package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>
{

	Optional<List<CartItem>> findByCart(Cart cart);

	@Query(value = "from CartItem ci inner join Cart ce on ci.cart.id = ce.id where ce.id = :userId and ci.productId = :productId")
	Optional<CartItem> findByUserIdAndProductId(int userId, int productId);

}
