<?php
    $con = mysqli_connect("localhost", "id1063903_hiteshkr", "123456", "id1063903_cfdiitk");

    $user_id = $_POST["user_id"];
    $subject = $_POST["subject"];
    $ci = $_POST["ci"];
    $topic = $_POST["topic"];
    $tom = $_POST["tom"];
    $tag = $_POST["tag"];
    $statement = mysqli_prepare($con, "INSERT INTO query2 (user_id, subject, topic, tom, ci, tag) VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "isssss", $user_id, $subject, $topic, $tom, $ci, $tag);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>