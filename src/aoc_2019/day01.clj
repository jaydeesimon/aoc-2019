(ns aoc-2019.day01
  (:require [aoc-2019.misc :refer [reducible-input parse-long]]))

(defn fuel [mass]
  (- (quot mass 3) 2))

(defn fuel2 [mass]
  (transduce
    (comp
      (take-while pos?)
      (drop 1))
    +
    0
    (iterate fuel mass)))

(defn fuel-requirements [fuel-fn]
  (transduce
    (comp
      (map parse-long)
      (map fuel-fn))
    +
    0
    (reducible-input)))

(defn part1 []
  (fuel-requirements fuel))

(defn part2 []
  (fuel-requirements fuel2))

(comment

  (part1)

  (part2)

  )