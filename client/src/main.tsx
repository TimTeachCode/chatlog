import React from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './index.css';
import Layout from './Layout';
import HelloRoute from './routes/HelloRoute';
import ChatLogRoute from './routes/ChatLogRoute';
import { ThemeProvider } from './providers/ThemeProvider';

const router = createBrowserRouter([
  {
    element: <Layout />,
    children: [
      {
        path: '/',
        element: <HelloRoute />,
      },
      {
        path: '/:id',
        element: <ChatLogRoute />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ThemeProvider>
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
