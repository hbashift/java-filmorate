# java-filmorate

## ER diagram of the Database
![alt text](https://github.com/hbashift/java-filmorate/blob/add-database/er-diagram.png?raw=true)

## Some query requests for example
### Top *n* film according to the amount of likes
```sql
SELECT *, COUNT(l.film_id) as count 
FROM film f 
LEFT JOIN likes l ON f.film_id=l.film_id
                  GROUP BY f.film_id 
                  ORDER BY count DESC
                  LIMIT {n}
```

### Update query for a film
```sql
UPDATE film
SET name = {name}, description = {description}, 
    release_date = {release_date}, duration = {duration}, 
    mpa_id = {mpa_id} 
            WHERE film_id = {film_id}
```

### Query for film's genre
```sql
SELECT g.genre_id, g.name
FROM genre g 
    INNER JOIN film_genre fg on g.genre_id = fg.genre_id
WHERE fg.film_id = {film_id}
ORDER BY g.genre_id
```

### Mutual friends query
```sql
SELECT user_id, email, login, name, birthday 
FROM users 
WHERE user_id IN (SELECT friend_id 
                  FROM FRIENDSHIP 
                  WHERE user_id = {user_id})
AND user_id IN (SELECT friend_id
                FROM FRIENDSHIP
                WHERE user_id = {user_id})
```