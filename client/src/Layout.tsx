import { Outlet } from 'react-router-dom';
import { Header } from '@/components/Header';
import { Footer } from '@/components/Footer';
import { Toaster } from '@/components/ui/sonner';

const Layout = () => {
  return (
    <>
      <Header />
      <main className='mt-32 mb-12'>
        <Outlet />
        <Toaster />
      </main>
      <Footer />
    </>
  );
};

export default Layout;
