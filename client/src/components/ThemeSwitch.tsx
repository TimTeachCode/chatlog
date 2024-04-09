import { useTheme } from '@/providers/ThemeProvider';
import { Button } from './ui/button';
import { MoonIcon, SunIcon } from 'lucide-react';

export const ThemeSwitch = () => {
  const { theme, setTheme } = useTheme();

  const handleClick = () => {
    setTheme(theme === 'light' ? 'dark' : 'light');
  };

  return (
    <Button onClick={handleClick} variant='ghost' size='icon'>
      {theme === 'light' ? (
        <MoonIcon className='h-6 w-6' />
      ) : (
        <SunIcon className='h-6 w-6 text-slate-300' />
      )}
    </Button>
  );
};
