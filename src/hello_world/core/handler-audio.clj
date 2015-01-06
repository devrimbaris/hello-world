(ns hello-world.core.handler-audio
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hello-world.core.utils :as utils]
            [clojure.string :as stri]
            [ring.util.response :as resp]
            [ring.middleware.session.memory :refer [memory-store]]
            [ring.middleware.params :as prms]
            [ring.middleware.stacktrace :as stack]
            [noir.session :as nses]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [hiccup.core :refer [html]]))

;; (select-word-to-display
;;  (let [three-choices [from-existing-list-of-audio  a-new-randomly-selected-image-file-and-checking-url hybrid-with-previous]])
;;  (do
;;    (get-current-mp3-list))
;;  )
;; ;;

;; (get-current-mp3-list)
;; ;;file-name
;; ;;cambridge.txt
;; ;;file structure
;; ;;	word|status|url
;; ;; word and url are self explaining,
;; ;; status is
;; ;;   :WORKING for words with working mp3 links
;; ;;   :DEAD for nonexistent mp3 links
;; ;;
;; ;;check if file exists
;; ;;IF YES
;; ;;	return a vector of maps [{:word xxx :url yyy}]


;; (creation of-options-of_5_pictures_with_one_of_them_as_AUDIO
;;  (let [word (select-word-to-display)]))

(defn load-current-mp3-list
  "Returns a vector of maps containing words, urls and statuses for cambridge mp3 urls by parsing a words list file.
  FILE STRUCTURE:word|status|url
  word and url are self explaining, status is
        WORKING for words with working mp3 links
        DEAD for nonexistent mp3 links."
  []
  (let [filename "resources/public/cambridge_mp3.txt"
        rows (clojure.string/split (slurp filename) #"\r\n")
        splitted-rows (for [r rows] (clojure.string/split r #"\|"))]
    (reduce
     (fn [m [word status url]]
       (conj m {:word word :status status :url url}))
     [] splitted-rows)))

(defn get-filerows-data
"Loads all card information from images directory and using this information returns a vector of maps, with each map :word, :status :url.  The url is
  parsed from each word's webpage in Cambridge dictionary."
  []
  (when-let [cards  (take 3 (utils/get-cards))]
    (for [c cards]
      (if-let [url (utils/get-mp3-url (stri/lower-case (:word c)))]
        {:word (stri/lower-case (:word c)) :status :WORKING :url url}
        {:word (stri/lower-case (:word c)) :status :DEAD :url nil} ))))

(defn save-mp3-links []
  (spit "resources/public/cambridge_mp3.txt"
        (reduce str (for [{x :word y :status z :url}  (get-mp3-filerows-data)] (str x "|" y "|" z "\n" )))))


(save-mp3-links)










