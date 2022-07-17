package com.example.easyworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(1,"Jumping Jacks", R.drawable.jumpingjacks,false,false)
        exerciseList.add(jumpingJacks)

        val stepsOntoChair = ExerciseModel(2,"Steps Onto Chair", R.drawable.step_onto_chair,false,false)
        exerciseList.add(stepsOntoChair)

        val highKneeRunning = ExerciseModel(3,"High Knee Running", R.drawable.high_knee_running,false,false)
        exerciseList.add(highKneeRunning)

        val lunges = ExerciseModel(4,"Lunges", R.drawable.lunges,false,false)
        exerciseList.add(lunges)

        val crunches = ExerciseModel(5,"Crunches", R.drawable.crunches,false,false)
        exerciseList.add(crunches)

        val plank = ExerciseModel(6,"Plank", R.drawable.plank,false,false)
        exerciseList.add(plank)

        val sidePlank = ExerciseModel(7,"Side Plank", R.drawable.side_plank,false,false)
        exerciseList.add(sidePlank)

        val tricepDipsChair = ExerciseModel(8,"Tricep Dips On Chair", R.drawable.ticep_dips_chair,false,false)
        exerciseList.add(tricepDipsChair)

        val pushUps = ExerciseModel(9,"Push Ups", R.drawable.push_ups,false,false)
        exerciseList.add(pushUps)

        val wallSit = ExerciseModel(10,"Wall Sit", R.drawable.wall_sit,false,false)
        exerciseList.add(wallSit)

        val squat = ExerciseModel(10,"Squat", R.drawable.sqaut,false,false)
        exerciseList.add(squat)


        return exerciseList
    }
}