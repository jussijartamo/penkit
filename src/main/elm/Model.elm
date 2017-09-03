module Model exposing (..)

import Material
import Time exposing (Time)
import Material.Snackbar as Snackbar

type alias Team =
  { name : String
  , overallKg : Int
  , recentlyKg : Int
  }

type alias Mdl =
    Material.Model

type Msg
  = Input String
  | Send
  | NewMessage String
  | Loc String
  | KgChange Float
  | LiftClicked
  | Tick Time
  | Snackbar (Snackbar.Msg String)
  | Mdl (Material.Msg Msg)

type alias Model =
  { input : String
  , messages : List String
  , wsAddress : String
  , lift : Float
  , snackbar: Snackbar.Model String
  , mdl: Material.Model
  }
