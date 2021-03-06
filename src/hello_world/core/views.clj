(ns hello-world.core.views
  (:require [hello-world.core.utils :as utils]
            [hello-world.core.bulmaca :as bulmaca]
            [clojure.string :as stri]
            [hiccup.page :refer [html5]]
            [hiccup.core :refer [html]]))


;;usage ;   [:p (embed-audio selected-card)]
(defn embed-audio [audio-url]
  "Map vasitasiyla gecilen card bilgilerine gore bir audio dosyasi linki ekler."
  (html [:audio {:controls "controls"
                 :autoplay "true"
                 :src audio-url
                 :type "audio/mpeg"} [:p]])
  )

(defn do-print-results [result-map]
  (let [entries (for [x result-map] x)]
    entries))

;;__ html page generators
(defn print-question-form [selected-card options question-text ]
  (html
   [:table {:border "1"}
    [:tr
     [:td {:colspan "3"} [:div { :style "font-size:xx-large;" } question-text]]]
    [:tr
     [:td {:valign "top" } [:img {:width "320" :src (:img-file selected-card)  }]]
     [:td {:valign "top"} [:div {:style "font-size:xx-large; width: 180px; word-wrap: break-word"} [:form {:action "/check-answer" :method "GET" :id "checkoo" :name "checkoo"}
                                    (for [x options]
                                      [:p {:style "padding:7px 7px 7px 7px;"}
                                       [:label
                                        [:input {:type "radio"
                                                 :style "visibility:hidden;"
                                                 :name "answer"
                                                 :onclick "document.getElementById('checkoo').submit();"
                                                 :value (:card-id x)}]
                                        (:word x)]])]]]
     
     [:td {:valign "top" } [:canvas {:style "border:1px solid red;" :width 550 :height 420 :ID "canvas"} "Canvas tag not supported"]] ]]))

(defn- print-question [selected-card  options question-text {imgURL :imgURL slices :slices shown-slice-ids :shown-ids}]
  (html [:html
         [:script (bulmaca/get-slice-js imgURL slices shown-slice-ids)]
         [:head [:title "Word maze"]]
         [:body {:onLoad "drawImage()"}
          (print-question-form selected-card options question-text)
          ]]))

(defn html-print-question [feedback c-progress c-cards selected-card options question-text puzzle-data-map]
  (str
   (html [:p  (str  feedback  "     (" c-progress " / " c-cards ")")])
   (print-question selected-card options question-text puzzle-data-map)))

(defn print-remaining-cards [rem-cards]
  (let [id-list (utils/find-all-values-in-map-with-key :card-id rem-cards)]
    (html [:p (reduce str (interpose "-" id-list))])))

(defn print-options [ordered-map]
  (html [:html
          [:head
           [:title "Word maze"]
           [:link {:rel "stylesheet" :href "http://yui.yahooapis.com/pure/0.5.0/pure-min.css"}]
]
 
         [:body
          [:div {:style "font-size:xx-large;"}
           [:p  [:a {:href "/start-word-maze"} "Word Tree Cards"]]
           [:p  [:a {:href "/audio/start-audio"} "Audio hearing"]]

           (for [m ordered-map] [:p [:a  {:href
                                          (str "/ordered/start-ordered?selection=" (:category m))} (:category m)]]
                )]]]))
