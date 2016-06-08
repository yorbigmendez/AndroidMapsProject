<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\Place;
use Illuminate\Support\Facades\Input;

class PlaceController extends Controller
{
    public function index (){
      $Place = Place::all();
      return response()->json($Place);
    }

//    public function store() {
//      $place = new Place;
//      $place->name = Input::get ('name');
//      $place->latitude = Input::get ('latitude');
//      $place->longitude = Input::get ('longitude');
//      $place->save();
//      return response()->json($place);
//    }

    public function store(Request $request) {
        $encuesta = new Place;

        $encuesta->descripcion = $request->input('name');
        $encuesta->estado = $request->input('latitude');
        $encuesta->fechaCreacion = $request->input('longitude');

        $encuesta->save();

        return 'true';
    }

    public function update($id) {
      $place = Place::find($id);
      $place->name = Input::get ('name');
      $place->latitude = Input::get ('latitude');
      $place->longitude = Input::get ('longitude');
      $place->save();
      return response()->json($place);
    }

    public function destroy($id) {
      $place = Place::find($id)->delete();
    }
}
