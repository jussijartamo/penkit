module Penkit exposing (..)

import Material.Helpers exposing (map2nd)
import WebSocket
import Debug exposing(log)
import Navigation exposing (Location)
import Model exposing (..)
import Material
import PenkitView exposing (view)
import Time exposing (every, second)
import Platform.Sub exposing (batch)
import Material.Snackbar as Snackbar
import Html
import Json.Encode exposing (..)

locParser: Location -> Msg
locParser location = Loc location.hostname

main =
  Navigation.program locParser
    { init = init
    , view = view
    , update = update
    , subscriptions = subscriptions
    }

combineAddress: String -> String
combineAddress hostname = "ws://" ++ hostname ++ ":4567/chat"

init : Location -> (Model, Cmd Msg)
init location =
  (Model "" [] (combineAddress location.hostname) 80 Snackbar.model Material.model , Cmd.none)


--sm: Snackbar.Contents String
--sm = Snackbar.toast "ABC" "DEF"
--sv = Snackbar.view sm

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
  case msg of
    Input newInput ->
      ({ model | input = newInput }, Cmd.none)

    Tick time ->
        (model, Cmd.none)

    LiftClicked ->
        let

            (c) = Snackbar.toast "ABC" "DEF"

            (snackbar_, effect) =
                  Snackbar.add c Snackbar.model
                    |> map2nd (Cmd.map Snackbar)

            --(l) =
            --    (log (Json.Encode.encode 4 effect))
            --(asdf) = Snackbar.add c

            --(modsku, comsku) = (asdf Snackbar.model) |> map2nd (Cmd.map Snackbar)

            --(vfd) = Snackbar.view modsku

            --(bsfd) = (Html.map comsku vfd)
           -- (snackbar_) =
             --   (Snackbar.view (Snackbar.add (Snackbar.toast "dsf" <| "sdf")))
                --Snackbar.add (Snackbar.toast "dsf" <| "Toast message #")
                    --|> map2nd (Cmd.none)
        in
            (model, effect )

    Send ->
          (model, WebSocket.send model.wsAddress model.input)

    NewMessage str ->
      ({model | messages = (str :: model.messages)}, Cmd.none)

    KgChange newValue ->
      ({ model | lift = newValue}, Cmd.none)

    Loc hostname ->
        ({model | wsAddress = (combineAddress hostname)}, Cmd.none)

    Snackbar msg ->
        let
            (l) = log ("sdfre")
        in
            (model, Cmd.none)
        --({model | snackbar =abdf }, Cmd.none)

    Mdl msg_ ->
        Material.update Mdl msg_ model


subscriptions : Model -> Sub Msg
subscriptions model =
  (batch [every second Tick
  , WebSocket.listen model.wsAddress NewMessage])

