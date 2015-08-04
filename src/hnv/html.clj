(ns hnv.html
  (:require [hiccup.page :refer [html5]]
            [clojure.core.reducers :as r]
            [hnv.core :as query]))

(def head
  [:head
    [:meta {:http-equiv "Content-Type" :content "text/html" :charset "UTF-8"}]
    [:title "Hnv"]
      [:link
        {:type "text/css" :rel "stylesheet" :href "/resources/css/materialize.min.css"
         :media "screen,projection"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]])

(def navbar
  [:nav {:class "light-blue lighten-1" :role "navigation"}
    [:div {:class "nav-wrapper container"}
      [:a {:id "logo-container" :href "/" :class "brand-logo"} "Hnv"]
      [:ul {:class "right hide-on-med-and-down"}
        [:li [:a {:href " "} [:i {:class "mdi-navigation-refresh"}]]]]
        ]])
      ;;[:ul {:id "nav-mobile" :class "side-nav"}
        ;;[:li [:a {:href " "} [:i {:class "mdi-navigation-refresh"}]]]]
      ;;[:a {:href "#" :data-activates "nav-mobile" :class "button-collapse"}
        ;;[:i {:class "material-icons"} "menu"]]]])

(def body1
  [:div {:class "section no-pad-bot" :id "index-banner"}
    [:div {:class "container"}
      [:br] [:br]
      [:h1 {:class "header center orange-text"} "Hnv"]
      [:div {:class "row center"}
        [:h5 {:class "header col s12 light"}
          "A simple HackerNews viewer"]]
      [:div {:class "row center"}
        [:a {:href "/tops"
             :id "download-button"
             :class "btn-large waves-effect waves-light orange"}
             "TOP 10 News"]]
      [:div {:class "row center"}
        [:a {:href "/latest"
             :id "download-button"
             :class "btn-large waves-effect waves-light orange"}
             "Latest News"]]
      [:div {:class "row center"}
        [:a {:href "/scores"
             :id "download-button"
             :class "btn-large waves-effect waves-light orange"}
             "High socre News"]]
      [:br] [:br]]])

(def body2
  [:div {:class "container"}
    [:div {:class "section"}
      [:div {:class "row"}
        [:div {:class "col s12 center"}
          [:h3 [:i {:class "mdi-content-send brown-text"}]]
          [:h4 "Introduction"]
          [:h5 {:class "light"} [:p]]
          [:h5 {:class "light"}
            "Hnv is read-only HackerNews Viewer and use HackerNews API."]]]]
    [:br] [:br]])

(def footer
  [:footer {:class "page-footer orange"}
    [:div {:class "container"}
      [:div {:class "row"}
        [:div {:class "col l6 s12"}
          [:h5 {:class "white-text"} "" ]
          [:p {:class "grey-text text-lighten-4"} " "]]
        [:div {:class "col l3 s12"}
          [:h5 {:class "white-text"}" "]
          [:ul
            [:li [:a {:class "white-text" } " "]]]]
        [:div {:class "col l3 s12"}
          [:h5 {:class "white-text"}"Connect"]
            [:br]
            [:iframe {:src
                      "https://ghbtns.com/github-btn.html?user=ak1t0&repo=Hnv&type=watch&count=true&size=large"
                      :allowtransparency "true" :frameborder "0" :scrolling "0"
                      :width "170" :height "30"}]
            [:br] [:br]]]]
    [:div {:class "footer-copyright"}
      [:div {:class "container"}
        "Made by "
        [:a {:class "orange-text text-lighten-3"
             :href "http://materializecss.com"}
             "Materialize"]]]])
(def top
  [:body
    [:script
      {:type "text/javascript"
       :src "https://code.jquery.com/jquery-2.1.1.min.js"}]
    [:script
      {:type "text/javascript"
       :src "/resources/js/materialize.min.js"}]
    navbar body1 body2 footer
    ])

(def topnews-bar
  [:div {:class "container"}
    [:div {:class "row center"}
      [:h3 {:class "header col s12 light"}
        "TOP News"]]])

(defn containize [x]
  [:div {:class "row"}
    [:div {:class "container"} x]])

(defn cardnize [[user score time title url comments]]
  [:div {:class "col s12 m12"}
    [:div {:class "card hoverable"}
      [:div {:class "card-content"}
        [:h5 title]
        [:p [:span {:style "color:#bdbdbd;"} url]]
        [:p (str comments " comments " score " points " " by " user " " time " ago ")]]
      [:div {:class "card-action"}
        [:a {:href url} "Source"]
        [:a "More (under construction)"]]]])

(defn tcard []
  (->> (query/get-topstory)
    (take 10)
    (r/map query/get-json)
    (into [])
    (pmap query/format-json)
    (pmap cardnize)
    (containize)))

(defn topnews []
  [:body
    [:script
      {:type "text/javascript"
       :src "https://code.jquery.com/jquery-2.1.1.min.js"}]
    [:script
      {:type "text/javascript"
       :src "/resources/js/materialize.min.js"}]
    navbar topnews-bar (tcard) footer])

(def qnews-bar
  [:div {:class "container"}
    [:div {:class "row center"}
      [:h3 {:class "header col s12 light"}
        "High score News"]]])

(defn score? [x]
  (if (>= (second x) 100) x nil))

(defn qcard []
  (->> (query/get-topstory)
    (take 30)
    (r/map query/get-json)
    (into [])
    (pmap query/format-json)
    (pmap score?)
    (filter #(not= % nil))
    (sort-by second >)
    (take 10)
    (pmap cardnize)
    (containize)))

(defn qnews []
  [:body
    [:script
      {:type "text/javascript"
       :src "https://code.jquery.com/jquery-2.1.1.min.js"}]
    [:script
      {:type "text/javascript"
       :src "/resources/js/materialize.min.js"}]
    navbar qnews-bar (qcard) footer])

(def lnews-bar
  [:div {:class "container"}
    [:div {:class "row center"}
      [:h3 {:class "header col s12 light"}
        "Latest News"]]])

(defn lcard []
  (->> (query/get-newstory)
    (take 10)
    (r/map query/get-json)
    (into [])
    (pmap query/format-json)
    (pmap cardnize)
    (containize)))

(defn lnews []
  [:body
    [:script
      {:type "text/javascript"
       :src "https://code.jquery.com/jquery-2.1.1.min.js"}]
    [:script
      {:type "text/javascript"
       :src "/resources/js/materialize.min.js"}]
    navbar lnews-bar (lcard) footer])

(defn index []
  (html5 head top))

(defn top-viewer []
  (html5 head (topnews)))

(defn quality-viewer []
  (html5 head (qnews)))

(defn latest-viewer []
  (html5 head (lnews)))
