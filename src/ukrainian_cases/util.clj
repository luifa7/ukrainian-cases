(ns ukrainian-cases.util
  (:require [clojure.string :refer [join lower-case]])
  (:import (java.util HashMap)))

(defn get-last-letter
  [word]
  (str (subs word (- (count word) 1))))

(defn get-last-two-letters
  [word]
  (subs word (- (count word) 2)))

(defn get-letter-before-last
  [word]
  (str (first (get-last-two-letters word))))

(defn get-two-letters-before-last
  [word]
  (join (reverse (get-last-two-letters (join (reverse (subs word (- (count word) 3))))))))

(defn are-both-letters-equal?
  [two-letters]
  (cond
    (< (count two-letters) 2) (throw (AssertionError. "This requires exactly two letters"))
    (> (count two-letters) 2) (throw (AssertionError. "This requires exactly two letters")))
  (= (first (lower-case two-letters)) (last (lower-case two-letters))))

(defn string-in-vector?
  [string vector]
  (if (= nil (some #(= string %) vector))
    false
    true))

(defn normalize!
  "Some letters are the same but have different encoding when they come from latin keyboards"
  [word]
  (clojure.string/replace word #"A|a|B|E|e|I|i|M|H|O|o|P|p|C|c|T|y|X|x" {"A" "А"
                                                                         "a" "а"
                                                                         "B" "В"
                                                                         "E" "Е"
                                                                         "e" "е"
                                                                         "I" "І"
                                                                         "i" "і"
                                                                         "M" "М"
                                                                         "H" "Н"
                                                                         "O" "О"
                                                                         "o" "о"
                                                                         "P" "Р"
                                                                         "p" "р"
                                                                         "C" "С"
                                                                         "c" "с"
                                                                         "T" "Т"
                                                                         "y" "у"
                                                                         "X" "Х"
                                                                         "x" "х"
                                                                         }))

(defn convert-to-java-hashmap
  [clojure-map]
  (def java-map (atom (new HashMap)))
  (doseq [[key value] clojure-map]
    (doto @java-map (.put key value)))
  @java-map)
