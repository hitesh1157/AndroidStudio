<?php
    $con = mysqli_connect("localhost", "id1063903_hiteshkr", "123456", "id1063903_cfdiitk");

    $query_id = $_POST["query_id"];
    $user_id = $_POST["user_id"];

    $statement = mysqli_prepare($con, "SELECT * FROM query2 WHERE query_id = ? AND user_id = ?");
    mysqli_stmt_bind_param($statement, "ii", $query_id, $user_id);
    mysqli_stmt_execute($statement);

    $statement2 = mysqli_prepare($con, "DELETE * FROM query2 WHERE query_id = ? AND user_id = ?");
    mysqli_stmt_bind_param($statement2, "ii", $query_id, $user_id);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $query_id, $user_id, $subject, $topic, $tom, $ci, $tag);

    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = false;
        mysqli_stmt_execute($statement2);
    }

    echo json_encode($response);
?>
