(ns tictactoe.consoleui
  (:require [tictactoe.core :as ttt]))


(defn print-board
  [board]
  (dorun (map println (partition 3 board))))

(defn prompt-and-read-move
  [board token]
  (print-board board)
  (print (str "Enter your move (" token "): "))
  (flush)
  (read))

(defn print-game-over
  [game]
  (print-board (:board game))
  (if (ttt/winner? (:board game))
    (println (:next-token game) "won!!!")
    (println "Cat's game")))

(defn play
  [game]
  (let [move (prompt-and-read-move (:board game) (:current-token game))
        new-game (ttt/play-1-move
                  game
                  move)]
    (if (ttt/game-over? (:board new-game))
      (print-game-over new-game)
      (recur new-game))))

(def start-game
  (ttt/make-game ttt/empty-board 'X 'O))

(defn -main
  "I don't do a whole lot ... yet."
  [& _]
  (play start-game))
