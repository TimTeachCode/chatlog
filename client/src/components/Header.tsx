import { Logo } from './Logo';
import { ThemeSwitch } from './ThemeSwitch';

export const Header = () => {
  return (
    <header className='fixed w-full bg-background z-50 top-0 py-6 px-6 border-b shadow-sm transition-colors'>
      <div className='max-w-screen-lg mx-auto flex justify-between items-center'>
        <div className='hidden lg:block' />
        <Logo />
        <ThemeSwitch />
      </div>
    </header>
  );
};
