<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\Rout;

class RoutController extends Controller
{
    public function getAll() {
        return Rout::all();
    }

    public function store(Request $request) {
        $rout = new Rout;

        $rout->id = $request->input('id');
        $rout->name = $request->input('name');
        $rout->start = $request->input('start');
        $rout->end = $request->input('end');
        $rout->owner_id = $request->input('owner_id');

        $rout->save();

        return 'true';
    }

    public function destroy($id) {
        $rout = Rout::find($id);

        $rout->delete();

        return 'true';
    }

    public function getRoutByPerson($ownerId) {
        $rout = Rout::select('rout.id', 'rout.name', 'rout.start', 'rout.end', 'rout.owner_id')
            ->where('owner_id', $ownerId)
            ->get();
        return $rout;
    }
}
