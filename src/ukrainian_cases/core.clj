(ns ukrainian-cases.core
  (:gen-class
    :name ukrainian.cases.core
    :methods
    [#^{:static true} [getGendersNominative [String] java.util.HashMap]
     #^{:static true} [getPluralsNominative [String] java.util.HashMap]])
  (:require [ukrainian-cases.cases.nominative :refer [get-possible-genders-nominative
                                                      get-possible-plurals-nominative]]
            [ukrainian-cases.util :refer [convert-to-java-hashmap]])
  )

(defn -getGendersNominative
  "A Java-callable wrapper around the 'get-possible-genders-nominative' function."
  [word]
  (convert-to-java-hashmap (get-possible-genders-nominative word)))

(defn -getPluralsNominative
  "A Java-callable wrapper around the 'get-get-possible-plurals-nominative-genders-nominative' function."
  [word]
  (convert-to-java-hashmap (get-possible-plurals-nominative word)))
