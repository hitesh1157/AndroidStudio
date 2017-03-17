Intent intentSearch = new Intent(UserAreaActivity.this, MainActivity.class);
intentSearch.putExtra("name", name);
intentSearch.putExtra("userID", userID);
UserAreaActivity.this.startActivity(intentSearch);
