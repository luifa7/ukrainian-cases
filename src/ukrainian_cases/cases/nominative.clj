;;;; This file defines all functions related to the ukrainian nominative case

(ns ukrainian-cases.cases.nominative
  (:require [clojure.string :refer [ends-with? lower-case]]
            [ukrainian-cases.ukrainian-alphabet :refer [consonants vowels semivowels soft-sign
                                                        only-one-soft-vowel? using-ukrainian-alphabet?]]
            [ukrainian-cases.util :refer [are-both-letters-equal? get-last-letter get-letter-before-last
                                          get-two-letters-before-last normalize! string-in-vector?]]))

(def gender-masculine "masculine")
(def gender-neuter "neuter")
(def gender-feminine "feminine")

(def singular-endings ["Б" "б" "Г" "г" "Ґ" "ґ" "Д" "д" "Ж" "ж" "З" "з" "К" "к"
                       "Л" "л" "М" "м" "Н" "н" "П" "п" "Р" "р" "С" "с" "Т" "т"
                       "Ф" "ф" "Х" "х" "Ц" "ц" "Ч" "ч" "Ш" "ш" "Щ" "щ" "Й" "й"
                       "В" "в" "О" "о" "Е" "е" "О" "о" "Я" "я" "Ь" "ь" "А" "а"])

(def singular-endings-masculine ["Б" "б" "Г" "г" "Ґ" "ґ" "Д" "д" "Ж" "ж" "З" "з" "К" "к"
                                 "Л" "л" "М" "м" "Н" "н" "П" "п" "Р" "р" "С" "с" "Т" "т"
                                 "Ф" "ф" "Х" "х" "Ц" "ц" "Ч" "ч" "Ш" "ш" "Щ" "щ" "Й" "й"
                                 "В" "в" "О" "о" "Ь" "ь"])

(def singular-endings-neuter ["Е" "е" "О" "о" "Я" "я"])

(def singular-endings-feminine ["А" "а" "Я" "я" "Ь" "ь"])

(def plural-endings ["И" "и" "І" "і" "Ї" "ї" "А" "а" "Я" "я"])

(def plural-exceptions-feminine {"дівчина" "дівчата"
                                 "дитина" "діти"
                                 "людина" "люди"})

(def words-without-plural ["кафе" "кава" "кіно" "молоко" "пиво" "фото" "сало"])

(def words-without-singular ["окуляри" "штани" "джинси" "ножиці"])

(def singular-zero-ending-exceptions-feminine ["зустріч" "ніч" "піч" "січ" "любов" "подорож"])

(defn plural-exception-feminine?
  [word]
  (contains? plural-exceptions-feminine (lower-case word)))

(defn get-plural-for-exception-feminine
  [word]
  (get plural-exceptions-feminine (lower-case word)))

(defn word-without-plural?
  [word]
  (string-in-vector? (lower-case word) words-without-plural))

(defn word-without-singular?
  [word]
  (string-in-vector? (lower-case word) words-without-singular))

(defn zero-ending-exception?
  "Some zero ending words are an exception and are feminine"
  [word]
  (string-in-vector? (lower-case word) singular-zero-ending-exceptions-feminine))

(defn possible-plural?
  [word]
  (string-in-vector? (get-last-letter word) plural-endings))

(defn possible-singular?
  [word]
  (string-in-vector? (get-last-letter word) singular-endings))

(defn possible-singular-masculine?
  [word]
  (string-in-vector? (get-last-letter word) singular-endings-masculine))

(defn possible-singular-neuter?
  [word]
  (if (ends-with? word "я")
    (and (are-both-letters-equal? (get-two-letters-before-last word))
         (string-in-vector? (get-letter-before-last word) consonants))
    (string-in-vector? (get-last-letter word) singular-endings-neuter)))

(defn possible-singular-feminine?
  [word]
  (or (zero-ending-exception? word)
      (if (string-in-vector? (get-last-letter word) singular-endings-feminine)
        (if (ends-with? word "я")
          (or (not (are-both-letters-equal? (get-two-letters-before-last word)))
              (not (string-in-vector? (get-letter-before-last word) consonants)))
          true)
        false)))

(defn special-case-masculine?
  "If a word has this kind of format -> дім has to change the i to o for the plural -> доми"
  [word]
  (and (string-in-vector? (get-last-letter word) consonants)
       (or (= (str (first (get-two-letters-before-last word))) "і")
           (= (str (last (get-two-letters-before-last word))) "і"))
       (only-one-soft-vowel? word)
       (> 5 (count word))))

(defn get-plural-special-case-masculine
  [word]
  (str (clojure.string/replace word #"і" {"і" "о"}) "и"))

(defn special-case-feminine?
  "If a word has this kind of format -> сіль has to change the i to o for the plural -> солі
  and for this format -> піч it changes i for e -> печі"
  [word]
  (and (or (string-in-vector? (get-last-letter word) soft-sign)
           (string-in-vector? (get-last-letter word) consonants))
       (or (= (str (first (get-two-letters-before-last word))) "і")
           (= (str (last (get-two-letters-before-last word))) "і"))
       (only-one-soft-vowel? word)
       (> 5 (count word))))

(defn get-plural-special-case-feminine
  [word]
  (if (ends-with? word "ь" )
    (str (subs (clojure.string/replace word #"і" {"і" "о"}) 0 (- (count word) 1)) "i")
    (str (clojure.string/replace word #"і" {"і" "е"}) "i")))

(defn get-plural-masculine
  [word]
  (cond
    (string-in-vector? (get-last-letter word) consonants) (if (special-case-masculine? word)
                                                            (get-plural-special-case-masculine word)
                                                            (str word "и"))
    (ends-with? word "о") (str (subs word 0 (- (count word) 1)) "и")
    (ends-with? word "ь" ) (str (subs word 0 (- (count word) 1)) "i")
    (ends-with? word "й" ) (if (= (get-letter-before-last word) "и")
                             (str (subs word 0 (- (count word) 2)) "i")
                             (str (subs word 0 (- (count word) 1)) "ї"))))

(defn get-plural-neuter
  [word]
  (cond
    (ends-with? word "я" ) word
    (ends-with? word "о") (str (subs word 0 (- (count word) 1)) "a")
    (ends-with? word "е") (if (or (= (get-letter-before-last word) "ж")
                                  (= (get-letter-before-last word) "ш")
                                  (= (get-letter-before-last word) "щ"))
                            (str (subs word 0 (- (count word) 1)) "a")
                            (str (subs word 0 (- (count word) 1)) "я"))))

(defn get-plural-feminine
  [word]
  (cond
    (plural-exception-feminine? word) (get-plural-for-exception-feminine word)
    (zero-ending-exception? word) (if (special-case-feminine? word)
                                    (get-plural-special-case-feminine word)
                                    (str word "i"))
    (ends-with? word "ь") (if (special-case-feminine? word)
                            (get-plural-special-case-feminine word)
                            (str (subs word 0 (- (count word) 1)) "i"))
    (ends-with? word "a") (str (subs word 0 (- (count word) 1)) "и")
    (ends-with? word "я") (if (= (get-letter-before-last word) "і")
                            (str (subs word 0 (- (count word) 1)) "ї")
                            (str (subs word 0 (- (count word) 1)) "i"))))

(defstruct #^{:doc "Basic structure for nominative form of a word."}
           nominative :Gender :Singular :Plural :Message)

(defn get-gender-message
  [word gender]
  (condp = gender
    gender-masculine (cond
                       (ends-with? word "ь" ) "If the words means an object, it is masculine"
                       (ends-with? word "о" ) "If the words belongs to a proper name or has masculine meaning, it is masculine")
    gender-neuter (cond
                    (ends-with? word "о" ) "If the words don't belongs to a proper name and has no masculine meaning, it is neuter"
                    (ends-with? word "я" ) "Is neuter because it has double same consonant before last letter")
    gender-feminine (cond
                      (ends-with? word "ь" ) "If the words means a feeling, it is feminine"
                      (zero-ending-exception? word) "This is an exception for zero ending rule, it is feminine"
                      (plural-exception-feminine? word) "The plural of this word is an exception")))

(defn get-plural-message
  [word gender]
  (cond
    (word-without-plural? word) "This word has no plural"
    (word-without-singular? word) "This word has only plural"
    :else (get-gender-message word gender)))

(defn create-nominative
  "Gets the gender, singular and the plural form and creates a nominative struct"
  [gender singular plural message]
  (struct nominative gender singular plural message))

(defn create-nominative-masculine
  [singular]
  (create-nominative gender-masculine singular (get-plural-masculine singular) (get-plural-message singular gender-masculine)))

(defn create-nominative-neuter
  [singular]
  (create-nominative gender-neuter singular (get-plural-neuter singular) (get-plural-message singular gender-neuter)))

(defn create-nominative-feminine
  [singular]
  (create-nominative gender-feminine singular (get-plural-feminine singular) (get-plural-message singular gender-feminine)))

(defn get-possible-plurals
  "Returns a collection of structs with al the possible plurals for the possible genders of the word"
  [word]
  (def plurals (atom []))
  (if (and (possible-singular-masculine? word)
           (not (plural-exception-feminine? word))
           (not (zero-ending-exception? word)))
    (reset! plurals (conj @plurals (create-nominative-masculine word))))
  (if (and (possible-singular-neuter? word)
           (not (plural-exception-feminine? word))
           (not (zero-ending-exception? word)))
    (reset! plurals (conj @plurals (create-nominative-neuter word))))
  (if (possible-singular-feminine? word)
    (reset! plurals (conj @plurals (create-nominative-feminine word))))
  @plurals)

(defstruct #^{:doc "Basic structure for gender form of a word."}
           nominative-gender :Gender :Message)

(defn create-gender
  "Gets the gender and message and creates an struct"
  [gender message]
  (struct nominative-gender gender message))

(defn get-possible-genders
  "Returns a collection of strings with al the possible genders"
  [word]
  (def genders (atom []))
  (if (and (possible-singular-masculine? word)
           (not (plural-exception-feminine? word))
           (not (zero-ending-exception? word)))
    (reset! genders (conj @genders (create-gender gender-masculine (get-gender-message word gender-masculine)))))
  (if (and (possible-singular-neuter? word)
           (not (plural-exception-feminine? word))
           (not (zero-ending-exception? word)))
    (reset! genders (conj @genders (create-gender gender-neuter (get-gender-message word gender-neuter)))))
  (if (possible-singular-feminine? word)
    (reset! genders (conj @genders (create-gender gender-feminine (get-gender-message word gender-feminine)))))
  @genders)

(defstruct #^{:doc "Basic structure for results, with error message if there is some"}
           result :Result :Error)

(defn get-possible-plurals-nominative
  "It returns the possible plural form for a word in (possible) singular"
  [word]
  (cond
    (not (using-ukrainian-alphabet? (normalize! word))) (struct-map result
                                                          :Result []
                                                          :Error "This word has one or more not ukrainian characters")
    (word-without-singular? (normalize! word)) (struct-map result
                                                 :Result (create-nominative nil nil (normalize! word) (get-plural-message word nil))
                                                 :Error nil)
    (word-without-plural? (normalize! word)) (struct-map result
                                               :Result (create-nominative nil (normalize! word) nil (get-plural-message word nil))
                                               :Error nil)
    (possible-singular? (normalize! word)) (struct-map result
                                             :Result (get-possible-plurals (normalize! word))
                                             :Error nil)
    (possible-plural? (normalize! word)) (struct-map result
                                           :Result []
                                           :Error "This word is a possible plural")
    :else (struct-map result
                   :Result []
                   :Error "This word has no nominative singular format")))

(defn get-possible-genders-nominative
  "It returns the possible genders for word in (possible) singular"
  [word]
  (cond
    (not (using-ukrainian-alphabet? (normalize! word))) (struct-map result
                                                :Result []
                                                :Error "This word has one or more not ukrainian characters")
    (possible-singular? (normalize! word)) (struct-map result
                                :Result (get-possible-genders (normalize! word))
                                :Error nil)
    (possible-plural? (normalize! word)) (struct-map result
                              :Result []
                              :Error "This word is a possible plural")
    :else (struct-map result
            :Result []
            :Error "This word has no nominative singular format")))
