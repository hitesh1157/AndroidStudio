<?php
    $con = mysqli_connect("localhost", "id1063903_hiteshkr", "123456", "id1063903_cfdiitk");

    $user_id = $_POST["user_id"];
    $topic = $_POST["topic"];
    $subject = $_POST["subject"];

    $statement = mysqli_prepare($con, "SELECT * FROM query2 WHERE user_id = ? AND topic = ? AND subject = ?");
    mysqli_stmt_bind_param($statement, "iss", $user_id, $topic, $subject);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $query_id, $user_id, $subject, $topic, $tom, $ci, $tag);

    $response = array();
    $data = array();
    $data["success"] = false;
    $data["data"] = array();

    while(mysqli_stmt_fetch($statement)){
        $data["success"] = true;
        $response["query_id"] = $query_id;
        $response["subject"] = $subject;
        $response["topic"] = $topic;
        $response["user_id"] = $user_id;
        $response["tom"] = $tom;
        $response["ci"] = $ci;
        $response["tag"] = $tag;


        $data["data"][] = $response;
    }

    echo json_encode($data);
?>
