(ns ukrainian-cases.test
  (:require [clojure.test :refer :all]
            [ukrainian-cases.ukrainian-alphabet :refer :all]
            [ukrainian-cases.cases.nominative :refer [get-possible-genders-nominative result]]))

(deftest is-ukrainian-letter-test
  (testing "FIXME, I fail."
    (is (ukrainian-alphabet-contains-letter? "o"))
    (is (not (ukrainian-alphabet-contains-letter? "t")))
    ))

(deftest contains-only-one.soft-vowel-test
  (testing "FIXME, I fail."
    (is (only-one-soft-vowel? "hhhhi"))
    (is (not (only-one-soft-vowel? "hhohhi")))
    ))

(deftest is-word-written-in-ukrainian-alphabet-test
  (testing "FIXME, I fail."
    (is (using-ukrainian-alphabet? "дівчата"))
    (is (not (using-ukrainian-alphabet? "дівчатаlg")))
    ))

(deftest get-gender-masculine-test
  (testing "FIXME, I fail."
    (is (= (get-possible-genders-nominative "Музей") (struct-map result
                                                       :Result [{:Gender "masculine"
                                                                 :Message ""}]
                                                       :Error "")))
    ))

(deftest get-gender-neuter-test
  (testing "FIXME, I fail."
    (is (= (get-possible-genders-nominative "Море") (struct-map result
                                                      :Result [{:Gender "neuter"
                                                                :Message ""}]
                                                      :Error "")))
    ))

(deftest get-gender-feminine-test
  (testing "FIXME, I fail."
    (is (= (get-possible-genders-nominative "Крaїнa") (struct-map result
                                                        :Result [{:Gender "feminine"
                                                                  :Message ""}]
                                                        :Error "")))
    ))

(deftest get-gender-multiple-possibilities-test
  (testing "FIXME, I fail."
    (is (= (get-possible-genders-nominative "кіно") (struct-map result
                                                      :Result [{:Gender "masculine"
                                                                :Message "If the words belongs to a proper name or has masculine meaning"}
                                                               {:Gender "neuter"
                                                                :Message "If the words don't belongs to a proper name and has no masculine meaning"}]
                                                      :Error "")))
    ))

(deftest get-gender-no-plural-test
  (testing "FIXME, I fail."
    (is (= (get-possible-genders-nominative "кафе") (struct-map result
                                                      :Result [{:Gender "neuter"
                                                                :Message ""}]
                                                      :Error "")))
    ))

(deftest get-gender-no-singular-test
  (testing "FIXME, I fail."
    (is (= (get-possible-genders-nominative "окуляри") (struct-map result
                                                         :Result []
                                                         :Error "This word is a possible plural")))
    ))

;; (run-tests)
