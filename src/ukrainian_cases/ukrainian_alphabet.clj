;;;; This file defines all functions related to the ukrainian alphabet

(ns ukrainian-cases.ukrainian-alphabet
  (:require [clojure.string :refer [lower-case]]
            [ukrainian-cases.util :refer [normalize! string-in-vector?]]))

(def alphabet ["А" "а" "Б" "б" "В" "в" "Г" "г" "Ґ" "ґ" "Д" "д" "Е" "е"
               "Є" "є" "Ж" "ж" "З" "з" "И" "и" "І" "і" "Ї" "ї" "Й" "й"
               "К" "к" "Л" "л" "М" "м" "Н" "н" "О" "о" "П" "п" "Р" "р"
               "С" "с" "Т" "т" "У" "у" "Ф" "ф" "Х" "х" "Ц" "ц" "Ч" "ч"
               "Ш" "ш" "Щ" "щ" "Ю" "ю" "Я" "я" "Ь" "ь" "'"])

(def vowels ["А" "а" "Е" "е" "Є" "є" "И" "и" "І" "і" "Ї" "ї" "О" "о"
             "У" "у" "Ю" "ю" "Я" "я"])

(def semivowels ["В" "в" "Й" "й"])

(def consonants ["Б" "б" "Г" "г" "Ґ" "ґ" "Д" "д" "Ж" "ж" "З" "з" "К" "к"
                 "Л" "л" "М" "м" "Н" "н" "П" "п" "Р" "р" "С" "с" "Т" "т"
                 "Ф" "ф" "Х" "х" "Ц" "ц" "Ч" "ч" "Ш" "ш" "Щ" "щ"])

(def soft-sign ["Ь" "ь"])

(def symbols ["'"])

(defn ukrainian-alphabet-contains-letter?
  [letter]
  (string-in-vector? (normalize! letter) alphabet))

(defn only-one-soft-vowel?
  "Control every letter of the word to find if there is only one soft vowel: a y o e i и"
  [word]
  (def counter (atom 0))
  (doseq [letter (distinct (normalize! (lower-case word)))]
    (when (string-in-vector? (str letter) ["а" "е" "и" "і" "о" "у"])
      (swap! counter inc)))
  (= @counter 1))

(defn using-ukrainian-alphabet?
  "Control every letter of the word to find if the word correspond to the ukrainian alphabet"
  [word]
  (def ukrainian? (atom true))
  (doseq [letter (distinct (normalize! word))]
    (when (not (ukrainian-alphabet-contains-letter? (str letter)))
      (reset! ukrainian? false)))
  @ukrainian?)
