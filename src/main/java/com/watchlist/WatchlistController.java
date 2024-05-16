package com.watchlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.userRepo.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserWatchlists(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            List<Watchlist> watchlists = watchlistService.getUserWatchlists(user);
            return ResponseEntity.ok(watchlists);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/{username}/add")
    public ResponseEntity<?> addToWatchlist(@PathVariable String username, @RequestParam String symbol) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Watchlist watchlist = watchlistService.addToWatchlist(user, symbol);
            return ResponseEntity.ok(watchlist);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/{username}/remove/{watchlistId}")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable String username, @PathVariable Long watchlistId) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            try {
                watchlistService.removeFromWatchlist(user, watchlistId);
                return ResponseEntity.ok("Stock removed from watchlist successfully");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
