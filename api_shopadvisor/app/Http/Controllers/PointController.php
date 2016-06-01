<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\Point;

class PointController extends Controller
{
    public function getAll() {
        return Point::all();
    }

    public function store(Request $request) {
        $point = new Point;

        $point->id = $request->input('id');
        $point->x = $request->input('x');
        $point->y = $request->input('y');
        $point->rout_id = $request->input('rout_id');

        $point->save();

        return 'true';
    }

    public function destroy($id) {
        $point = Point::find($id);

        $point->delete();

        return 'true';
    }
}
