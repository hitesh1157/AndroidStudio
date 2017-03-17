<?php
    $con = mysqli_connect("localhost", "id1063903_hiteshkr", "123456", "id1063903_cfdiitk");

    $query_id = $_POST["query_id"];
    $user_id = $_POST["user_id"];

    $statement = mysqli_prepare($con, "DELETE FROM query2 WHERE query_id = $query_id AND user_id = $user_id");
    // mysqli_stmt_bind_param($statement, "ii", $query_id, $user_id);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
