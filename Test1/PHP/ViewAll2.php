<?php
    $con = mysqli_connect("localhost", "id1063903_hiteshkr", "123456", "id1063903_cfdiitk");

    $subject = $_POST["subject"];

    $statement = mysqli_prepare($con, "SELECT * FROM query WHERE subject = ?");
    mysqli_stmt_bind_param($statement, "s", $subject);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $queryID, $subject, $topic, $tom, $ci, $tag);

    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["subject"] = $subject;
        $response["topic"] = $topic;
        $response["tom"] = $tom;
        $response["ci"] = $ci;
    }

    echo json_encode($response);
?>