import { logo } from '@/assets';
import { Link } from 'react-router-dom';

export const Logo = () => {
  return (
    <Link to='/' className='flex items-center gap-x-3'>
      <img
        src={logo}
        width={80}
        height={80}
        alt='logo'
        className='drop-shadow-md'
      />

      <span className='hidden lg:block text-5xl uppercase font-bold text-green drop-shadow-md'>
        ChatLog
      </span>
    </Link>
  );
};
