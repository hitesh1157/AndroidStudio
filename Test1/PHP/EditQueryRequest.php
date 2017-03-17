<?php
    $con = mysqli_connect("localhost", "id1063903_hiteshkr", "123456", "id1063903_cfdiitk");

    $query_id = $_POST["query_id"];

    $statement = mysqli_prepare($con, "SELECT * FROM query2 WHERE query_id = ?");
    mysqli_stmt_bind_param($statement, "i", $query_id);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $query_id, $user_id, $subject, $topic, $tom, $ci, $tag);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
      $response["success"] = true;
      $response["query_id"] = $query_id;
      $response["subject"] = $subject;
      $response["topic"] = $topic;
      $response["user_id"] = $user_id;
      $response["tom"] = $tom;
      $response["ci"] = $ci;
      $response["tag"] = $tag;
    }

    echo json_encode($response);
?>
