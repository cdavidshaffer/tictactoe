(ns tictactoe.core
  (:gen-class))

;; Board
;;
;; 0 | 1 | 2
;; 3 | 4 | 5
;; 6 | 7 | 8

(def empty-board (vec (repeat 9 '_)))

(defn make-move
  "Return the new board after placing token at cell in board.
   
   cell and token must be valid."
  [board cell token]
  (assoc board cell token))

(defn invalid-move-reason
  "returns nil if move is valid, otherwise returns a keyword indicating
   why the move is invalid."
  [board cell]
  (cond
    (not (int? cell)) :not-correct-type
    (< cell 0) :cell-number-too-small
    (> cell 8) :cell-number-too-large
    (not= '_ (nth board cell)) :cell-already-occupied
    :else nil))

(defn- winner-in-group?
  [board cell-group]
  (let [cell-contents (set (map
                            (fn [cell] (nth board cell))
                            cell-group))]
    (and (= 1 (count cell-contents))
         (not= '_ (first cell-contents)))))

(def winning-cell-groups
  [[0 1 2]
   [3 4 5]
   [6 7 8]
   [0 3 6]
   [1 4 7]
   [2 5 8]
   [0 4 8]
   [2 4 6]])

(defn winner?
  [board]
  (not-every? false?
              (map (partial winner-in-group? board)
                   winning-cell-groups)))

(defn- filled?
  [board]
  (every? (fn [token] (not= '_ token))
          board))

(defn game-over?
  [board]
  (or (winner? board)
      (filled? board)))

;; GameState = Board x Token x Token
;;
;; {:board a-board, :current-token a-token, :next-token a-token}
(defn make-game
  [board current-token next-token]
  {:board board, :current-token current-token, :next-token next-token})

(defn play-1-move
  "GameState x Cell -> GameState"
  [{current-token :current-token, next-token :next-token, current-board :board} cell]
  (let
   [next-board (make-move current-board cell current-token)]
    (make-game next-board next-token current-token)))
