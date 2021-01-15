package onlineShop.controller;

import onlineShop.entity.Cart;
import onlineShop.entity.CartItem;
import onlineShop.entity.Customer;
import onlineShop.entity.Product;
import onlineShop.service.CartItemService;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;
import onlineShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class CartItemController {
    // 删除所有cart item时使用
    @Autowired
    private CartService cartService;    // 用户在页面上传进来的

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CustomerService customerService;    // 通过email找到customer

    @Autowired
    private ProductService productService;

    @RequestMapping("/cart/add/{productId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addCartItem(@PathVariable(value = "productId") int productId) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();   // 通过 SecurityContext 找到 user email
        Customer customer = customerService.getCustomerByUserName(username);    // 通过customerService找到 customer

        Cart cart = customer.getCart(); // 通过customer找到cart
        List<CartItem> cartItems = cart.getCartItem();  // 找出购物车里已经有的商品

        Product product = productService.getProductById(productId); // 通过productId 找到product

        // if the item is already in the cart, update quantity and total price
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            if (product.getId() == (cartItem.getProduct().getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getProductPrice());
                cartItemService.addCartItem(cartItem);
                return;
            }
        }

        // if the item is not in the cart yet, add the product to user's cart
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setProduct(product);
        cartItem.setPrice(product.getProductPrice());
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);
    }

    @RequestMapping(value = "/cart/removeCartItem/{cartItemId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable(value = "cartItemId") int cartItemId) {
        cartItemService.removeCartItem(cartItemId);
    }

    //
    @RequestMapping(value = "/cart/removeAllItems/{cartId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeAllCartItems(@PathVariable(value = "cartId") int cartId) {
        Cart cart = cartService.getCartById(cartId);    // 通过cartId 找到 cart
        cartItemService.removeAllCartItems(cart);
    }
}
