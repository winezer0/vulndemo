import { Hono as Router } from 'hono';
import * as HonoModule from 'hono';
const express = require('express');

function first(ctx) { return ctx.text('first'); }
function second(ctx) { return ctx.text('second'); }
function third(ctx) { return ctx.text('third'); }
function ignored(ctx) { return ctx.text('ignored'); }

const api = new Router();
const alias = api;
api.get('/same', first);
alias.get('/alias', second);
const other = new Router();
other.get('/same', third);
const namespaced = new HonoModule.Hono();
namespaced.get('/namespace', third);

{
    const api = express();
    api.get('/express', ignored);
}

let mutable = new Router();
mutable.get('/before', first);
mutable = express();
mutable.get('/after', ignored);

function shadowConstructor(Router) {
    const api = new Router();
    api.get('/shadow-constructor', ignored);
}

function shadowReceiver(api) {
    api.get('/shadow-receiver', ignored);
}

function deferred() {
    mutable.get('/deferred', ignored);
}

const notHono = { get() {} };
notHono.get('/ordinary', ignored);
