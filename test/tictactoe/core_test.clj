(ns tictactoe.core-test
  (:require [clojure.test :refer :all]
            [tictactoe.core :refer :all]))

(deftest make-move-test
  (is (= '[_ _ _ _ X _ _ _ _]
         (make-move empty-board 4 'X)))
  (is (= '[O O _ _ X _ _ _ _]
         (make-move '[_ O _ _ X _ _ _ _] 0 'O))))

(deftest invalid-move-reason-test
  (is (= :not-correct-type (invalid-move-reason empty-board "monkey")))
  (is (= :cell-number-too-small (invalid-move-reason empty-board -1)))
  (is (= :cell-number-too-large (invalid-move-reason empty-board 9)))
  (is (= :cell-already-occupied
         (invalid-move-reason '[X _ _ _ O _ _ _ _]
                              4)))
  (is (not (invalid-move-reason empty-board 0)))
  (is (not (invalid-move-reason '[X _ _ _ O _ _ _ _] 1))))

(deftest winner?-test
  (is (not (winner? empty-board)))
  (is (winner? '[X X X _ _ _ _ _ _]))
  (is (not (winner? '[_ X O _ _ _ _ _ _])))
  (is (winner? '[_ _ _ X X X _ _ _]))
  (is (winner? '[O _ X O _ _ O _ _]))
  ;; ...
  )

(deftest game-over?-test
  (is (not (game-over? empty-board)))
  (is (game-over? '[X X X _ _ _ _ _ _]))
  (is (game-over? '[X O X
                    O X O
                    O X O])))

(deftest play-1-move-test
  (is (=
       {:board '[X _ _ _ _ _ _ _ _],
        :current-token 'O,
        :next-token 'X}
       (play-1-move
        {:board empty-board,
         :current-token 'X,
         :next-token 'O}
        0))))