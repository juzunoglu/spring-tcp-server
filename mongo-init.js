db.createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "social"
            }
        ]
    }
);

db = new Mongo().getDB("social");
