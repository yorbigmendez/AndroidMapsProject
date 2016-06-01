<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Points extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('points', function (Blueprint $table) {
            $table->increments('id');
            $table->float('x');
            $table->float('y');
            $table->integer('rout_id')->unsigned();
            $table->timestamps();

        });
        Schema::table('points', function (Blueprint $table) {
            $table->foreign('rout_id')->references('id')->on('rout')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('points');
    }
}
