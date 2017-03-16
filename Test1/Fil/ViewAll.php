<?php
    $con = mysqli_connect("localhost", "id1063903_hiteshkr", "123456", "id1063903_cfdiitk");

    $subject = $_POST["subject"];


    $statement = mysqli_prepare($con, "SELECT * FROM query WHERE subject = ?");
    mysqli_stmt_bind_param($statement, "s", $subject);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $query_id, $user_id, $subject, $topic, $tom, $ci, $tag);

    $response = array();
    $response_store = array();
    $response["success"] = false;
    $response["tom"] = 'xyz';
    $response_store["success"] = false;
    $p = 0;
    $c = 0;
    $m = 0;

    while(mysqli_stmt_fetch($statement)){


        $response_store["success"] = true;
        $response_store["topic"] = $topic;
        $response_store["ci"] = $ci;
        $response_store["subject"] = $subject;
        $response_store["tom"] = $tom;
        $response_store["tag"] = $tag;

        if($response["tom"] != $response_store["tom"]){
          $response["success"] = true;
          $response["topic"] = $response_store["topic"];
          $response["ci"] = $response_store["ci"];
          $response["subject"] = $response_store["subject"];
          $response["tom"] = $response_store["tom"];
          $response["tag"] = $response_store["tag"];


          echo json_encode($response);
        }


    }


?>