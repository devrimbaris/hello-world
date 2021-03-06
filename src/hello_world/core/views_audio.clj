(ns hello-world.core.views-audio
  (:require
   [hello-world.core.views :as vo]
   [clojure.string :as stri]
   [hiccup.core :refer [html]]
   [hiccup.page :refer [html5]]))


(def mmm {:selected-card
 {:url "http://dictionary.cambridge.org/media/american-english/us_pron/e/elb/elbow/elbow.mp3",
  :status :WORKING,
  :card-id 7,
  :category "body-parts",
  :word "elbow",
  :au-file "http://dictionary.cambridge.org/media/british/us_pron/e/elb/elbow/elbow.mp3",
  :img-file "body-parts/elbow.gif"},
 :options
 [{:card-id 3,
   :category "body-parts",
   :word "cheek",
   :au-file "http://dictionary.cambridge.org/media/british/us_pron/c/che/cheek/cheek.mp3",
   :img-file "body-parts/cheek.jpg"}
  {:card-id 4,
   :category "body-parts",
   :word "chin",
   :au-file "http://dictionary.cambridge.org/media/british/us_pron/c/chi/chin_/chin.mp3",
   :img-file "body-parts/chin.gif"}
  {:url "http://dictionary.cambridge.org/media/american-english/us_pron/e/elb/elbow/elbow.mp3",
   :status :WORKING,
   :card-id 7,
   :category "body-parts",
   :word "elbow",
   :au-file "http://dictionary.cambridge.org/media/british/us_pron/e/elb/elbow/elbow.mp3",
   :img-file "body-parts/elbow.gif"}
  {:card-id 2,
   :category "body-parts",
   :word "back",
   :au-file "http://dictionary.cambridge.org/media/british/us_pron/b/bac/back_/back.mp3",
   :img-file "body-parts/back.gif"}
  {:card-id 1,
   :category "body-parts",
   :word "arm",
   :au-file "http://dictionary.cambridge.org/media/british/us_pron/a/arm/arm__/arm.mp3",
   :img-file "body-parts/arm.gif"}]})


(defn- with-page-template [f & args]
  (html5 [:head
          [:title "Ordered"]
          [:link {:rel "stylesheet" :href "http://yui.yahooapis.com/pure/0.5.0/pure-min.css"}]]
         [:body (apply f args)]))


(defn get-question-text [{mp3url :url}  options]
  (html
   [:table
    [:tr
     [:td [:div {:style "font-size:xx-large;"} "Duydugun kelimeyi işaretler misin?"]]]
    [:tr
     [:td [:div {:style "font-size:xx-large;"} (vo/embed-audio  mp3url)]]]]
   [:table 
    [:tr  [:td  [:form {:action "/audio/check-answer" :method "GET" :id "checkoo" :name "checkoo"}
                 (for [x options]
                   [:td {:style "padding:20px 20px 20px 20px;"} [:label {:style "font-size:xx-large;"}
                                                                 [:input {:type "radio" :style "visibility:hidden;" :name "answer" :onclick "document.getElementById('checkoo').submit();" :value (:word x) }]
                                                                 [:img {:width  "120" :src (str "/" (:img-file x))}]
                                                                 ]])
                 ]
           

           ]]]))

(defn print-audio-question [{selected-card :selected-card options :options} ]
  (with-page-template get-question-text selected-card options)
  )

(defn- get-report [ & all]
  (apply str all))

(defn print-report [progress]
  (with-page-template get-report progress)
  )

;(print-audio-question mmm)

