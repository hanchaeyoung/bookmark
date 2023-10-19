package spring.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.bookmark.entity.BookEntity;
import spring.bookmark.entity.CartEntity;
import spring.bookmark.entity.UserEntity;
import spring.bookmark.repository.BookRepository;
import spring.bookmark.repository.CartRepository;
import spring.bookmark.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    /* 장바구니 */
    @PostMapping("/{bookId}")
    public String addToCart(@PathVariable("bookId") Long bookId, @RequestParam("quantity") int quantity, Authentication authentication) {
        // 사용자 정보 가져오기
        String username = authentication.getName();
        UserEntity user = userRepository.findById(username).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (user != null && book != null) {
            // 해당 사용자와 책을 기반으로 장바구니 항목을 찾거나 생성
            CartEntity cartItem = cartRepository.findByUserAndBook(user, book);
            if (cartItem == null) {
                cartItem = new CartEntity();
                cartItem.setUser(user);
                cartItem.setBook(book);
            }

            // 현재 장바구니에 담겨있는 수량
            int currentQuantity = cartItem.getQuantity();

            // 총 재고
            int totalStock = book.getStock();

            // 총 수량 (현재 장바구니 수량 + 새로 담을 수량)
            int totalQuantity = currentQuantity + quantity;

            if (totalQuantity <= totalStock) {
                // 장바구니에 수량을 업데이트
                cartItem.setQuantity(totalQuantity);
                cartRepository.save(cartItem);

                // 재고 감소
                int updatedStock = totalStock - quantity;
                book.setStock(updatedStock);
                bookRepository.save(book);
            } else {
                // 재고가 부족한 경우 처리 (예를 들어, 에러 메시지 출력 또는 리디렉션)
                return "redirect:/?error=stock_shortage";
            }
        }

        return "redirect:/";
    }

    @GetMapping("/")
    public String viewCart(Authentication authentication, Model model) {
        String username = authentication.getName();
        UserEntity user = userRepository.findById(username).orElse(null);

        if (user != null) {
            List<CartEntity> cartItems = cartRepository.findByUser(user);
            model.addAttribute("cartItems", cartItems);
        }

        return "board/cart.html";
    }

    /* 장바구니 삭제 */
    @PostMapping("/remove/{cartId}")
    public String removeFromCart(@PathVariable("cartId") Long cartId) {
        CartEntity cartItem = cartRepository.findById(cartId).orElse(null);

        if (cartItem != null) {
            // 해당 상품의 수량과 정보 가져오기
            int quantity = cartItem.getQuantity();
            BookEntity book = cartItem.getBook();

            // 장바구니에서 상품 삭제
            cartRepository.delete(cartItem);

            // 상품의 재고 복원
            int updatedStock = book.getStock() + quantity;
            book.setStock(updatedStock);
            bookRepository.save(book);
        }

        return "redirect:/cart/";
    }

    /* 주문하기 */
//    @PostMapping("/checkout")
//    public String checkout(Authentication authentication) {
//        String username = authentication.getName();
//        UserEntity user = userRepository.findById(username).orElse(null);
//
//        if (user != null) {
//            List<CartEntity> cartItems = cartRepository.findByUser(user);
//            for (CartEntity cartItem : cartItems) {
//                // 상품 삭제
//                cartRepository.delete(cartItem);
//            }
//        }
//
//        return "redirect:/checkout"; // 주문 완료 페이지로 리다이렉트
//    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        // 주문이 완료되었음을 나타내는 메시지를 모델에 추가
        model.addAttribute("message", "주문이 완료되었습니다.");

        // 장바구니 비우기
        cartRepository.deleteAll();

        return "board/checkout.html"; // 주문 완료 페이지로 이동
    }

}