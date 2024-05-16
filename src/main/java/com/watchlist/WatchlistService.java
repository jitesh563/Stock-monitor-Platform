package com.watchlist;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.User;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public List<Watchlist> getUserWatchlists(User user) {
        return watchlistRepository.findByUser(user);
    }

    public Watchlist addToWatchlist(User user, String symbol) {
        Watchlist watchlist = new Watchlist(user, symbol);
        return watchlistRepository.save(watchlist);
    }

    public void removeFromWatchlist(User user, Long watchlistId) {
        Optional<Watchlist> optionalWatchlist = watchlistRepository.findById(watchlistId);
        if (optionalWatchlist.isPresent() && optionalWatchlist.get().getUser().equals(user)) {
            watchlistRepository.delete(optionalWatchlist.get());
        } else {
            throw new IllegalArgumentException("Watchlist not found or does not belong to the user");
        }
    }
}
