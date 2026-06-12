require('dotenv').config();
const express = require('express');
const { Pool } = require('pg');

const app = express();
app.use(express.json());
const pool = new Pool({
  connectionString: 'postgresql://postgres:123456789@localhost:5432/first-steps-android',

});

// GET /posts
app.get('/posts', async (req, res) => {
  try {
    const { rows } = await pool.query('SELECT id, created_at FROM logs ORDER BY created_at DESC');
    res.json(rows);
  } catch (err) {
    console.error('Error GET /posts', err);
    res.status(500).json({ error: 'Error al obtener posts' });
  }
});

// POST /posts
app.post('/posts', async (req, res) => {
  try {
    const { rows } = await pool.query(
      'INSERT INTO logs DEFAULT VALUES RETURNING id, created_at'
    );
    res.status(201).json(rows[0]);
  } catch (err) {
    console.error('Error POST /posts', err);
    res.status(500).json({ error: 'Error al crear post' });
  }
});

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`API lista en puerto ${port}`));