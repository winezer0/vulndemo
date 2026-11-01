import { Hono } from 'hono';
const express = require('express');
import fastify from 'fastify';
const expressApp = express();
const fastifyApp = fastify();
const app = new Hono();
function expressHandler(req, res) { return res.send('express'); }
function fastifyHandler(req, reply) { return reply.send('fastify'); }
function honoHandler(ctx) { return ctx.text('hono'); }
expressApp.get('/express', expressHandler);
fastifyApp.get('/fastify', fastifyHandler);
app.get('/hono', honoHandler);
