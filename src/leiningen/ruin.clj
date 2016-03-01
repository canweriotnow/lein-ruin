(ns leiningen.ruin
  (:require [clojire.java.io :as io]
            [clojure.string :as str]))

(defn- enum-files []
  (let [wd (first (seq (.list (io/file "src"))))]
    (map #(.path %)
         (filter #(not (.isDirectory %))
                 (seq (.listFiles (io/file (str "src/" wd))))))))

(defn strip-parens [file-string]
  (str/replace file-string "(\(|\))" ""))

(defn ruin
  "I don't do a lot."
  [project & args]
  (let [files (enum-files)]
    (for [file files]
      (spit file (strip-parens (slurp file))))
    (println "RUIN!"))
  )
